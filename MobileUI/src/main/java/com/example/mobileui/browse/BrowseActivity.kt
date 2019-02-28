package com.example.mobileui.browse

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.mobileui.R
import com.example.mobileui.bookmarked.BookmarkedActivity
import com.example.mobileui.injection.ViewModelFactory
import com.example.mobileui.mapper.ProjectViewMapper
import com.example.mobileui.model.Project
import com.example.presentation.BrowseProjectViewModel
import com.example.presentation.model.ProjectView
import com.example.presentation.state.Resource
import com.example.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: BrowseAdapter
    @Inject
    lateinit var mapper: ProjectViewMapper
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var browseViewModel: BrowseProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        AndroidInjection.inject(this)
        browseViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BrowseProjectViewModel::class.java)
        setupBrowseRecycler()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupBrowseRecycler(){
        recycler_projects.layoutManager = LinearLayoutManager(this)
        recycler_projects.adapter = adapter
    }
    override fun onStart() {
        super.onStart()
        browseViewModel.getProjects().observe(this,
            Observer<Resource<List<ProjectView>>>{
                it?.let { resource ->
                    handleDatastate(resource)
                }
            })
        browseViewModel.fetchProjects()
    }

    private fun handleDatastate(resource:Resource<List<ProjectView>>){
        when (resource.status){
            ResourceState.SUCCESS->{
                setupScreenForSuccess(resource.data?.map {
                    mapper.mapToView(it)
                })
            }
            ResourceState.LOADING->{
                progress.visibility = View.VISIBLE
                recycler_projects.visibility = View.GONE
            }
        }
    }

    private fun setupScreenForSuccess(projects: List<Project>?){
        progress.visibility = View.GONE
        projects?.let {
            adapter.projects = it
            adapter.notifyDataSetChanged()
            recycler_projects.visibility = View.VISIBLE
        } ?: run {
            //todo:
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            return when(it.itemId){
                R.id.action_bookmarked->{
                    startActivity(BookmarkedActivity.getStartIntent(this))
                    true
                }else -> super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
