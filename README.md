**Movie Database**

**E-R Model**
The 3 main entities are:
- Movie
- Company
- Genre

The relationship entities are:
- CompanyProduction (Many-Many relationship between Company and Movie)
- MoveGenre (Many-Many relationship between Movie and Genre)

The summary reporting tables:
The summary reporting tables are the tables that will service our APIs. The summarized data is computed from the above relationship tables. Since the reporting APIs are get requests on already pre-stored data that will not change frequently, we can optimize for the get requests as read only. If any new data is added to the DB then, we can always compute the delta and update the changed record.  

I am storing the company details per year which includes the budget and revenue summed up. For the genre, I store the average popularity per genre per year.

- company_details (pk: {company_id, year}, budget, revenue) with an additional index on year
- genre_details (pk: {genre_id, year}, popularity) with an additional index on year

**Assumptions**
1. The company budget and revenue are attributed in full to each company per movie even if there are multiple companies involved in the production of the movie, since we don't have visibility into how the budget and revenue is split between them.
2. The popularity is attributed in full to each of the genres if there are multiple genres per movie.


**Architecture & App Set Up**
The architecture is an MVC spring-boot java application connecting to a PostgreSQL DB. I am using Slf4j and Logback for logging and OpenAPI for API documentation (Swagger-UI).

I am using docker to orchestrate the system. You can run the app by typing the commands below:

docker compose build
docker compose up

Once the app is started, the data is loaded from the movies_metadata.csv file and populated into the tables. The current batch size is hardcoded to 25,000, but it can be made configurable. That is, for every 25k movies read from the file, the data is flushed to the DB. Also, where the data is being read can be made configurable.

**API**

http://localhost:8080/swagger-ui.html

**Querying the API**
- Production Company Details
  - http://localhost:8080/api/v1/movieDb/companies/28205?year=1995
  - companyId is a path param
  - year is a query param
  - the company details for that year with budget and revenue is returned 
- Movie Genre Details  
  - http://localhost:8080/api/v1/movieDb/genres/popular?year=1995
  - year is a query param
  - the most popular genre for that year is returned


**Future Improvements**
- We definitely need to add unit tests and integration tests before we can have this code production ready.
- The reading of the csv file can be made more robust
- We can add metrics and performance tracking to the app
- We can add a cache layer to the app if the website becomes really popular.

