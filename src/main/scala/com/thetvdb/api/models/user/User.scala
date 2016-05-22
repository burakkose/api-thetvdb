package com.thetvdb.api.models.user

case class User(userName: String, language: String, favoritesDisplaymode: String)

case class UserFavorites(favorites: List[String])

case class UserResponse(data: User)

case class UserFavoritesResponse(data: UserFavorites)

case class UserRatings(ratingType: String, ratingItemId: Int, rating: Int)

case class UserRatingsResponse(data: List[UserRatings])