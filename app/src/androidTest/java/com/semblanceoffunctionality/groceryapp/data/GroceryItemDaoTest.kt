package com.semblanceoffunctionality.groceryapp.data

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.semblanceoffunctionality.groceryapp.utilities.DATABASE_NAME
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GroceryItemDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var groceryItemDao: GroceryItemDao
    private val wantedItem = GroceryItem("wanted", true)
    private val unwantedItem = GroceryItem("unwanted", false)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
        Log.println(Log.DEBUG,"tag","Got here")

        groceryItemDao = database.groceryItemDao()
        groceryItemDao.insertAll(listOf(wantedItem,unwantedItem))
    }

    @After fun closeDb() {
        database.close()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        context.deleteDatabase(DATABASE_NAME);
    }

    @Test
    fun testGetAllItems() = runBlocking {
        val resultList = groceryItemDao.getAll()
        assertThat(resultList.size, equalTo(2))
    }

    @Test
    fun testGetWantedItemsWhenNone() = runBlocking {
        val resultList = groceryItemDao.getAllWantedItems()

        assertThat(resultList.size, equalTo(1))
        assertThat(resultList[0], equalTo(wantedItem))
    }

    @Test
    fun testFindByName() = runBlocking {
        val resultList = groceryItemDao.findByName(unwantedItem.name)
        assertThat(resultList, equalTo(unwantedItem))
    }

    @Test
    fun testDeleteItem() = runBlocking {
        groceryItemDao.delete(wantedItem)
        val resultList = groceryItemDao.getAll()
        assertThat(resultList.size, equalTo(1))
        assertThat(resultList[0], equalTo(unwantedItem))
    }
}