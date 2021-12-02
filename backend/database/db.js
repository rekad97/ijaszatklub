const config = require('../config/config.json');
const mongoose = require('mongoose');

const connectionOptions = { useNewUrlParser: true, useUnifiedTopology: true };
mongoose.connect(process.env.MONGODB_URI, connectionOptions);
mongoose.Promise = global.Promise;

module.exports = {
    User: require('../models/user.js'),
    Team: require('../models/team.js'),
    Shot: require('../models/shot.js'),
    Setup: require('../models/setup.js'),
    Training: require('../models/training.js')
}
