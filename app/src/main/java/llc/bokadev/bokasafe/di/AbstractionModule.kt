package llc.bokadev.bokasafe.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import llc.bokadev.bokasafe.core.navigation.Navigator
import llc.bokadev.bokasafe.core.navigation.NavigatorImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractionModule {
    @Binds
    @Singleton
    abstract fun bindNavigator(
        navigatorImpl: NavigatorImpl
    ): Navigator
}