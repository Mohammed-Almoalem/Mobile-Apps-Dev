package com.example.todo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class TodoDBSystem(appContext: Context?) : SQLiteOpenHelper(appContext, db_name, null, db_ver) {

    companion object {
        private const val db_name = "TodoDB"
        private const val db_ver = 1
        private const val table_name = "Tasks"
        private const val col_task_id = "task_id"
        private const val col_task_title = "task_title"
        private const val col_task_des = "task_des"
        private const val col_task_done = "task_done"
        private const val col_task_theme = "task_theme"
    }

    // on DB create either for the first time or when it has been deleted
    // and re created

    override fun onCreate(p0: SQLiteDatabase) {
        val createDB = "CREATE TABLE $table_name(" +
                "$col_task_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$col_task_title TEXT," +
                "$col_task_des TEXT," +
                "$col_task_done TEXT," +
                "$col_task_theme TEXT" +
                ")"
        p0.execSQL(createDB)
    }

    // on DB upgrade

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {
        p0.execSQL("DROP TABLE IF EXISTS $table_name")
        onCreate(p0)
    }

    // create task

    fun createTask(title: String, des: String, theme: String) {
        val p0 = this.writableDatabase
        val task = ContentValues()
        task.put(col_task_title, title)
        task.put(col_task_des, des)
        task.put(col_task_done, "false")
        task.put(col_task_theme, theme)
        p0.insert(table_name, null, task)
        p0.close()
    }

    fun getTasks(isDone: Boolean = false): MutableList<Task> {
        val p0 = this.readableDatabase
        val cursor: Cursor = p0.rawQuery(
            "SELECT * FROM $table_name WHERE $col_task_done = '${if (!isDone) "false" else "true"}'",
            null
        )
        val tasks = mutableListOf<Task>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(col_task_id))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(col_task_title))
                val des = cursor.getString(cursor.getColumnIndexOrThrow(col_task_des))
                val done = cursor.getString(cursor.getColumnIndexOrThrow(col_task_done))
                val theme = cursor.getString(cursor.getColumnIndexOrThrow(col_task_theme))

                tasks.add(
                    Task(
                        id = id,
                        title = title,
                        des = des,
                        done = done,
                        theme = theme
                    )
                )
            } while (cursor.moveToNext())

            cursor.close()
            p0.close()
        }

        return tasks
    }

    fun setDone(isDone: Boolean, id: Int) {
        val p0 = this.writableDatabase
        val values = ContentValues().apply {
            put(col_task_done, (if (isDone) "true" else "false"))
        }
        p0.update(table_name, values, "$col_task_id = ?", arrayOf(id.toString()))
        p0.close()
    }

    fun clearTasks() {
        val p0 = this.writableDatabase
        val query = "DELETE FROM $table_name WHERE $col_task_done = 'true'"
        p0.execSQL(query)

    }
}

class Task(
    val id: Int = 0,
    val title: String,
    val des: String,
    val done: String,
    val theme: String
)
