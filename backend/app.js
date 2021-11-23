require('rootpath')();
const express = require('express');
const app = express();
const cors = require('cors');
const bodyParser = require('body-parser');
const jwt = require('./helpers/jwt');
const expressJwt = require('express-jwt');
const errorHandler = require('./helpers/error-handler');

var SECRET = "secret"
var mongoose = require('mongoose');

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(cors());

app.use(jwt());
app.use('/users', require('./controllers/user.controller'), expressJwt({ secret: SECRET, algorithms: ['RS256'] }));
app.use('/teams', require('./controllers/team.controller'), expressJwt({ secret: SECRET, algorithms: ['RS256'] }));
app.use('/shots', require('./controllers/shot.controller'), expressJwt({ secret: SECRET, algorithms: ['RS256'] }));
app.use('/trainings', require('./controllers/training.controller'), expressJwt({ secret: SECRET, algorithms: ['RS256'] }));
app.use('/setups', require('./controllers/setup.controller'), expressJwt({ secret: SECRET, algorithms: ['RS256'] }));

app.use(errorHandler);


const port = process.env.NODE_ENV === 'production' ? (process.env.PORT || 80) : 3000;
const server = app.listen(port, function() {
    console.log('Server listening on port ' + port);
})