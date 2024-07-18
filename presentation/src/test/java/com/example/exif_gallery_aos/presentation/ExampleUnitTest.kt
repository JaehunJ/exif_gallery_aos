package com.example.exif_gallery_aos.presentation

import com.example.exif_gallery_aos.domain.album.AlbumModel
import com.example.exif_gallery_aos.domain.album.AlbumRepository
import com.example.exif_gallery_aos.domain.album.GetAlbumListUseCase
import com.example.exif_gallery_aos.presentation.album_grid.AlbumGridPageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

data class FakeAlbumModel(
    override val albumId: Int,
    override val albumName: String,
    override val albumPath: String,
    override val firstImagePath: String
) : AlbumModel

class FakeAlbumRepository : AlbumRepository {
    private val list = mutableListOf<AlbumModel>()
    override fun getAlbumList(): Flow<List<AlbumModel>> = flow {
        repeat(10) { iter ->
            list.add(FakeAlbumModel(albumId = iter, albumName = "name$iter", albumPath = "path$iter", firstImagePath = "firstImagePath$iter"))
        }
        emit(list)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class AlbumGridPageViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    private val albumListTest = mutableListOf<AlbumModel>()
    private lateinit var viewModel: AlbumGridPageViewModel
    private lateinit var repository: AlbumRepository
    private lateinit var getAlbumListUseCaseTest: GetAlbumListUseCase

    @Before
    fun setup() {

        repository = FakeAlbumRepository()
        getAlbumListUseCaseTest = GetAlbumListUseCase(repository)

        viewModel = AlbumGridPageViewModel(getAlbumListUseCaseTest)

        repeat(10){iter->
            albumListTest.add(FakeAlbumModel(albumId = iter, albumName = "name$iter", albumPath = "path$iter", firstImagePath = "firstImagePath$iter"))
        }
    }

    @After
    fun down() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getAlbumList() is called, then update state`() = runTest {
        val state = viewModel.state.value
        viewModel.getAlbumList()
        delay(1000L)

        val result = viewModel.state.value.list
        assertEquals(albumListTest.size, result.size)
    }
}