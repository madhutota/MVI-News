package com.newsmvi.data.repository

import com.newsmvi.data.api.ApiService
import com.newsmvi.data.model.ArticleDTO
import com.newsmvi.data.model.NewsResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class NewsRepositoryImplTest{

    private lateinit var apiService: ApiService
    private lateinit var repositoryImpl: NewsRepositoryImpl

    @Before
    fun setup(){
        apiService = mock()
        repositoryImpl = NewsRepositoryImpl(apiService)
    }

    @Test
    fun `getTopRatedBBCNews returns list of articles`() = runTest {

        val dummyArticles = listOf(
            ArticleDTO(title = "Toby Carvery owner admits cutting down ancient Enfield oak tree", description = "The felling of a much-loved and centuries-old north London oak tree was initially reported to police.", urlToImage = "https://ichef.bbci.co.uk/ace/branded_news/1200/cpsprodpb/3d88/live/35c68980-19ee-11f0-97a9-a9d92cd3bdfa.jpg"),
            ArticleDTO(title = "Bangladesh disappeared: Uncovering a secret jail next to an international airport", description = "Six men in Bangladesh tell the BBC about how they were forced into detention under the now-deposed government.", urlToImage = "https://ichef.bbci.co.uk/ace/branded_news/1200/cpsprodpb/1c88/live/86669850-1064-11f0-ac9f-c37d6fd89579.jpg")
        )
        val response = NewsResponse(articleDTOS = dummyArticles)

        `when`(apiService.getTopRatedBBCNews("bbc-news")).thenReturn(response)

        // When
        val result = repositoryImpl.getTopRatedBBCNews("bbc-news")

        // Then
        assertEquals(2, result.size)
        assertEquals("Toby Carvery owner admits cutting down ancient Enfield oak tree", result[0].title)
        assertEquals("Bangladesh disappeared: Uncovering a secret jail next to an international airport", result[1].title)

        verify(apiService).getTopRatedBBCNews("bbc-news")
    }
}


