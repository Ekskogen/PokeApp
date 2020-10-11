# PokeApp

PokeApp is a simple app to get the list of all pokemons from pokeapi. It uses MVP + Hilt + Coroutines + PagedLibrary + Room

## Hilt

Using dependency injection with Hilt.

1. Create app class and use annotation @HiltAndroidApp
2. Create Modules with annotations @Module @InstallIn(ApplicationComponent::class). You can use different components like ActivityComponent.

```kotlin
This is how a Activity Modules looks like
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    @ActivityScoped
    fun provideListener(@ActivityContext context: Context): PokemonListener = context as MainActivity

    @ActivityScoped
    @Provides
    fun provideMainPresenter(pokemonDataSource: DataSource.Factory<Int, Pokemon>,
                             pagedListConfig: PagedList.Config,
                             fetchPokemonsUseCase: FetchPokemonsUseCase,
                             clearPokemonTableUseCase: ClearPokemonTableUseCase
    ):
            MainContract.Presenter = MainPresenter(pokemonDataSource, pagedListConfig, fetchPokemonsUseCase, clearPokemonTableUseCase)
}
```

## Room

Creating the DB with Room is easy. Create your Entities (use @Entity and @PrimaryKey), create daos (@Dao) and finally your DB

```kotlin
@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance
                ?: synchronized(this) { instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "pokemon")
                .fallbackToDestructiveMigration()
                .build()
    }
}
```
To convert a query in to a datasource from dao use:

```kotlin
    @Query("SELECT * FROM pokemon")
    fun dataSource(): DataSource.Factory<Int, Pokemon>
```

## Retrofit

Using retrofit for REST api consumption. Turn API into interface.

```kotlin
    @GET("pokemon")
    suspend fun getPokemons(@Query("limit") limit: Int = 20, @Query("offset") offset: Int = 0): PokemonResponse
```

## Paging library

We have two sources of data, local and remote. We will observe local as a DataSource and fetch items, store them in to local. Create livedata in presenter and observe it, remember to remove the observer when view drops.

```kotlin
private val pokemonFeed: LiveData<PagedList<Pokemon>> =
        LivePagedListBuilder<Int, Pokemon>(pokemonsDataSource, pageListConfig)
            .setBoundaryCallback(object : PagedList.BoundaryCallback<Pokemon>() {
                override fun onZeroItemsLoaded() {
                    super.onZeroItemsLoaded()
                    fetchPokemonsFromZero()
                }
                override fun onItemAtEndLoaded(itemAtEnd: Pokemon) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    fetchPokemon(paging = true)
                }
            })
            .build()

    private val pokemonFeedObserver = Observer<PagedList<Pokemon>> {
        view?.showPokemonsList(it)
        ...
    }
```

## Libraries
```groovy
    // UI
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation "com.google.android.material:material:1.2.1"
    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"

    // Hilt - Dagger
    implementation 'com.google.dagger:hilt-android:2.28.1-alpha'
    implementation "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02"
    kapt 'com.google.dagger:hilt-android-compiler:2.28.1-alpha'
    kapt "androidx.hilt:hilt-compiler:1.0.0-alpha02"

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.9.1'

    // Room
    def room_version = "2.2.5"
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-ktx:$room_version"
    testImplementation "androidx.room:room-testing:$room_version"

    // Epoxy
    implementation 'com.airbnb.android:epoxy:2.18.0'
    kapt 'com.airbnb.android:epoxy-processor:2.18.0'
    implementation 'com.airbnb.android:epoxy-paging:2.18.0'

    // Glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    kapt 'com.github.bumptech.glide:compiler:4.11.0'
```
