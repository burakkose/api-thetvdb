package com.thetvdb.api.utils

class TVDBException(message: String) extends RuntimeException(message)

class AuthException(message: String =
                    "Authentication is possible but" +
                      " has failed or not yet been provided.")
  extends TVDBException(message)
