package com.example.mobileui.browse

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.mobileui.R
import kotlinx.android.synthetic.main.activity_browse.*
import javax.inject.Inject

class BrowseActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: ProjectAdapter
    @Inject
    lateinit var mapper:ProjectViewMapper
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    private lateinit var browseViewModel: BrowseProjectsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        AndroidInjection.inject(this)
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
        browseBookmarkedViewModel.fetchProjects()
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
            return when (item.itemId){
                android.R.id.home -> {
                    finish()
                    true
                }else -> super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
