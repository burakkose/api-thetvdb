## api-thetvdb
This is an API client for the thetvdb.com website for Scala.

## TheTVDB.com
TheTVDB.com is an open database for television content.

### API Key Registration

You need to request an API Key from the thetvdb.com website: [http://thetvdb.com/?tab=apiregister].

Please follow these guidelines:

* If you will be using the API information in a commercial product or website, you must email [scott@thetvdb.com](mailto:scott@thetvdb.com) and wait for authorization before using the API. However, you MAY use the API for development and testing before a public release.
* If you have a publicly available program, you MUST inform your users of this website and request that they help contribute information and artwork if possible.
* You MUST familiarize yourself with our data structure, which is detailed in the wiki documentation.
* You MUST NOT perform more requests than are necessary for each user. This means no downloading all of our content (we'll provide the database if you need it). Play nice with our server.
* You MUST NOT directly access our data without using the documented API methods.
* You MUST keep the email address in your account information current and accurate in case we need to contact you regarding your key (we hate spam as much as anyone, so we'll never release your email address to anyone else).
* Please feel free to contact us and request changes to our site and/or API. We'll happily consider all reasonable suggestions.

*Source: thetvdb.com*

## Installation

Just fork for now. This is not ready yet.

## Documentation

The official API documentation can be found here: [https://api.thetvdb.com/swagger].

## Usage

Create an instance of TheTVDB using your apiKey.

### Start

```
val tvDB = new TvDBApi("api_key")
tvDB.buildConnection
```

or

`val tvDB = TvDBApi("api_key")` and you can use 

`def refreshToken(): Future[Option[TokenResponse]]` for refreshing token.

### Languages
````
def getLanguages(): Future[Option[List[Language]]]
def getLanguage(langID: String): Future[Option[Language]]
````

### Episodes
````
def getEpisode(episodeID: String): Future[Option[Episode]]
````

### Series
````
def getSeries(id: String): Future[Option[Series]]
def searchSeries(searchKey: String): Future[Option[List[SeriesSearchData]]]
def getActors(seriesID: String): Future[Option[List[Actor]]]
````

### Updates
````
def getUpdates(fromTime: String, toTime: Option[String] = None): Future[Option[List[Update]]]
````
