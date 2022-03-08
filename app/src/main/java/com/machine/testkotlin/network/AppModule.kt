package com.machine.testkotlin.network
import android.app.Application
import androidx.room.Room
import com.machine.testkotlin.room.EmpDatabase
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi():Moshi = Moshi.Builder().build()


    @Provides
    @Singleton
    fun provideEmpDatabase(
        app: Application
    ) = Room.databaseBuilder(app, EmpDatabase::class.java, "emp_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideEmpDao(db: EmpDatabase) = db.empListDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope