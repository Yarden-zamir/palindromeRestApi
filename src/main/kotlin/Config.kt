package judo.kotlinPoc.plugins

import io.ktor.application.*
/** @return value of [entryName] found in .conf file or in environment (in that order).*/
fun Application.getCfg(entryName: String): String {
    return (environment.config.property(entryName).getString())
}