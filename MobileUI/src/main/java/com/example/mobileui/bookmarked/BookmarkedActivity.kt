package com.example.mobileui.bookmarked

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.mobileui.R
import com.example.mobileui.injection.ViewModelFactory
import com.example.mobileui.mapper.ProjectViewMapper
import com.example.mobileui.model.Project
import com.example.presentation.model.ProjectView
import com.example.presentation.state.BrowseBookmarkedViewModel
import com.example.presentation.state.Resource
import com.example.presentation.state.ResourceState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_browse.*
import timber.log.Timber
import javax.inject.Inject

class BookmarkedActivity : AppCompatActivity() {

    @Inject lateinit var adapter: BookmarkedAdapter
    @Inject lateinit var mapper: ProjectViewMapper
    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var browseBookmarkedViewModel: BrowseBookmarkedViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, BookmarkedActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked)
        AndroidInjection.inject(this)
        browseBookmarkedViewModel = ViewModelProviders.of(this,viewModelFactory)
            .get(BrowseBookmarkedViewModel::class.java)
        setupBrowseRecycler()
    }

    override fun onStart() {
        super.onStart()
        browseBookmarkedViewModel.getProjects().observe(this,
            Observer<Resource<List<ProjectView>>>{
                it?.let { resource ->
                    handleDatastate(resource)
                }
            })
        browseBookmarkedViewModel.fetchProjects()
    }

    private fun setupBrowseRecycler(){
        recycler_projects.layoutManager = LinearLayoutManager(this)
        recycler_projects.adapter = adapter
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
            adapter.bookmarkedProjects = it
            adapter.notifyDataSetChanged()
            recycler_projects.visibility = View.VISIBLE
        } ?: run {
            //todo:
        }
    }

}
