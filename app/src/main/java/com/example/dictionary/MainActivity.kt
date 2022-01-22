package com.example.dictionary

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.feature_dictionary.presentation.WordInfoAdapter
import com.example.dictionary.feature_dictionary.presentation.WordInfoViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityMainBinding
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: WordInfoViewModel by viewModels()
    private val adapter by lazy {
        WordInfoAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            etSearch.setText(viewModel.searchQuery.value)

            rv.adapter = adapter
            rv.addItemDecoration(DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            ))

            etSearch.addTextChangedListener {
                viewModel.onSearch(it.toString())
            }

            lifecycleScope.launchWhenStarted {

                viewModel.state.collectLatest { state ->
                    val list = state.wordInfoItems
                    adapter.submitList(list)

                    progressBar.isVisible = state.isLoading
                }

                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is WordInfoViewModel.UIEvent.ShowSnackbar -> {
                            Snackbar.make(view, event.msg, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        /*setContent {
            DictionaryTheme {
                val viewModel: WordInfoViewModel = hiltViewModel()
                val state = viewModel.state.value
                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is WordInfoViewModel.UIEvent.ShowSnackbar -> {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    event.msg
                                )
                            }
                        }
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState
                ) {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colors.background)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            TextField(
                                value = viewModel.searchQuery.value,
                                onValueChange = viewModel::onSearch,
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text(text = "Search...") }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(state.wordInfoItems.count()) { index ->
                                    val wordInfo = state.wordInfoItems[index]
                                    if (index > 0) {
                                        Spacer(modifier = Modifier.height(16.dp))
                                    }
                                    WordInfoItem(wordInfo = wordInfo)
                                    if (index < state.wordInfoItems.count() - 1) {
                                        Divider()
                                    }
                                }
                            }
                        }
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }*/
    }
}








