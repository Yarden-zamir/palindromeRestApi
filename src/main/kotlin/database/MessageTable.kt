package database

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object MessageTable : IntIdTable() {
    val text = varchar("text",512)
    val datePosted = varchar("postedDate",512) //switch those to datetime
    val dateEdited = varchar("dateEdited",512)
}