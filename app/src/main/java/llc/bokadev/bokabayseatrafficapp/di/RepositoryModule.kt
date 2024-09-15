package llc.bokadev.bokabayseatrafficapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import llc.bokadev.bokabayseatrafficapp.data.repository.LocationRepositoryImpl
import llc.bokadev.bokabayseatrafficapp.domain.repository.LocationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository
}
