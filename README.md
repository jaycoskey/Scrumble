# Scrumble overview

Scrumble is a proof-of-concept Scrum-tracking app.
It is written in JavaFX, and incorporates parsing of user-entered commands
with database calls and tabular display of the resulting data.
It is not designed to be a full-fledged app, but could be useful to someone learning JavaFX.

# Getting started

Scrumble is written in JavaFX using NetBeans.
To run properly, it requires a database to first be set up with certain tables.
The DDL for these tables can be found in **/resources/scrumble_ddl.sql**. 

There is also a small amount of test data in the file **/resources/scrumble_test_data.sql**.

# Scrumble commands
* list features. TODO: Add usage statement
* list owners. TODO: ...
* list statuses. TODO: ...
* list tasks. TODO: ...
* Structure to support many other commands that aren't currently implemented.  (See in-app Help for more info.)

# Main potential directions of improvement

## Functionality changes
* Completion of currently unsupported commands.
* Support for charts—primarily burndown charts.
* Support for editing data directly in the displayed tables.
* Add a back button to the Help window.
* Formation of a programmatic interface, using REST.
* URL components could be derived from the "signature" field in UserCmdTypeInfo.

## Other changes
* Execute SQL commands in a separate thread from the UI.
* Improve test data (see **/resources/scrumble_test_data.sql**).
* Adopt one or more tools to modify the design of service calls or data binding.
  * afterburner.fx: An MVP framework that uses annotations to @Inject data, per JSR 330.
  * DataFX: Package to facilitate multiple aspects of retrieving, formatting, and editing of data. Several tutorials are available.
  * Guice: Another way to @Inject data dependencies, by Google.

