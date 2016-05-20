package com.thetvdb.api.models.language

case class Language(
                     id: Int,
                     abbreviation: String,
                     name: String,
                     englishName: String
                   )

case class LanguageResponse(data: Language)

case class LanguagesResponse(data: List[Language])

