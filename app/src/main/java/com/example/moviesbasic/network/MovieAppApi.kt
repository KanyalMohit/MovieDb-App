package com.example.moviesbasic.network


import com.example.moviesbasic.data.detail.DetailDto
import com.example.moviesbasic.data.images.ImageDto
import com.example.moviesbasic.data.movieCast.CastDto
import com.example.moviesbasic.data.popular.PopularMovieDto
import com.example.moviesbasic.data.recommendation.RecommendationDto
import com.example.moviesbasic.data.searchResult.SearchDto
import com.example.moviesbasic.data.similar.SimilarDto
import com.example.moviesbasic.data.toprated.TopRatedMovieDto
import com.example.moviesbasic.data.tvSeries.TvSeriesDto
import com.example.moviesbasic.data.upcoming.UpComingDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://api.themoviedb.org/3/"

val json = Json {
    ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface MovieAppApi {
    @GET("movie/{category}")
    suspend fun getPopularMovieList(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") api_key : String = API_KEY
    ) : PopularMovieDto

    @GET("movie/{category}")
    suspend fun getTopMovieList(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") api_key : String = API_KEY
    ) : TopRatedMovieDto

    @GET("tv/{category}")
    suspend fun getNowPlayingList(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") api_key : String = API_KEY
    ) : TvSeriesDto

    @GET("movie/{category}")
    suspend fun getUpcomingList(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") api_key : String = API_KEY
    ) : UpComingDto

    @GET("search/movie")
    suspend fun getSearchList(
        @Query("query") query : String,
        @Query("page") page: Int,
        @Query("api_key") api_key: String = API_KEY
    ):SearchDto

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImage(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = API_KEY
    ) : ImageDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = API_KEY
    ):DetailDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = API_KEY
    ) : CastDto

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendation(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = API_KEY
    ) : RecommendationDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilar(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String = API_KEY
    ) : SimilarDto

}
object MovieApi{
    val retrofitService : MovieAppApi by lazy {
        retrofit.create(MovieAppApi::class.java)
    }
}