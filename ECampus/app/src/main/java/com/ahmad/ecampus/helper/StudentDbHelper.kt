package com.ahmad.ecampus.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ahmad.ecampus.model.Student

class StudentDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db_campus"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_STUDENT_ID = "student_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_BIRTH_DATE = "birth_date"
        private const val COLUMN_GENDER = "gender"
        private const val COLUMN_ADDRESS = "address"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_STUDENT_ID TEXT,
                $COLUMN_NAME TEXT,
                $COLUMN_BIRTH_DATE TEXT,
                $COLUMN_GENDER TEXT,
                $COLUMN_ADDRESS TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insert(student: Student): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STUDENT_ID, student.studentId)
            put(COLUMN_NAME, student.name)
            put(COLUMN_BIRTH_DATE, student.birthDate)
            put(COLUMN_GENDER, student.gender)
            put(COLUMN_ADDRESS, student.address)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun update(student: Student): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STUDENT_ID, student.studentId)
            put(COLUMN_NAME, student.name)
            put(COLUMN_BIRTH_DATE, student.birthDate)
            put(COLUMN_GENDER, student.gender)
            put(COLUMN_ADDRESS, student.address)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(student.id.toString()))
    }

    fun delete(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun getAll(): List<Student> {
        val list = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COLUMN_ID DESC")

        if (cursor.moveToFirst()) {
            do {
                val student = Student(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    studentId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    birthDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                    gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                    address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                )
                list.add(student)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun getById(id: Int): Student? {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, "$COLUMN_ID=?", arrayOf(id.toString()), null, null, null)
        if (cursor.moveToFirst()) {
            val student = Student(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                studentId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                birthDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
            )
            cursor.close()
            return student
        }
        cursor.close()
        return null
    }
}
