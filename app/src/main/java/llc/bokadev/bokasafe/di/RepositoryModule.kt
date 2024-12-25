package llc.bokadev.bokasafe.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import llc.bokadev.bokasafe.data.repository.DataStoreImplementation
import llc.bokadev.bokasafe.data.repository.LocationRepositoryImpl
import llc.bokadev.bokasafe.data.repository.BokaSafeRepositoryImplementation
import llc.bokadev.bokasafe.domain.repository.BokaSafeRepository
import llc.bokadev.bokasafe.domain.repository.DataStoreRepository
import llc.bokadev.bokasafe.domain.repository.LocationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    @Singleton
    abstract fun bindRepository(
        sparkyRepositoryImpl: BokaSafeRepositoryImplementation
    ): BokaSafeRepository

    @Binds
    @Singleton
    abstract fun bindDataStoreRepository(
        dataStoreImpl: DataStoreImplementation
    ): DataStoreRepository
}
