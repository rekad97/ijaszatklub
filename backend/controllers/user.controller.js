const express = require('express');
const router = express.Router();
const userService = require('../services/user.service');
const trainingService = require('../services/training.service');
const { Training } = require('../database/db');
const shotService = require('../services/shot.service')
router.post('/auth', auth);
router.post('/register', register);
router.get('/', getAll);
router.get('/current', getCurrent);
router.get('/:id', getById);
router.get('/:id/trainings', getTrainings);
router.get('/:id/setups', getSetups);
router.get('/:id/maxPoints', getMaxPoints);
router.get('/:id/averagePoints', getAveragePoints);
router.get('/:id/bestTraining', getBestTrainings);
router.get('/:id/teams', getTeams);
router.delete('/:id', _delete);

module.exports = router;

function auth(req, res, next) {
    console.log(req.body)
    userService.auth(req.body)
        .then(user => user ? res.json(user) : res.status(400).json({ message: 'Username or password is incorrect!' }))
        .catch(err => next(err));
}

function register(req, res, next) {
    userService.create(req.body)
        .then((user) => res.json(user))
        .catch(err => next(err));
}

function getAll(req, res, next) {
    userService.getAll()
        .then(users => res.json(users))
        .catch(err => next(err));
}

function getCurrent(req, res, next) {
    userService.getById(req.user.sub)
        .then(user => user ? res.json(user) : res.sendStatus(404))
        .catch(err => next(err));
}


function getById(req, res, next) {
    var obj;
    userService.getById(req.params.id)
        .then(user => {
            user ? res.json(user) : res.sendStatus(404);
        })
        .catch(err => {
            next(err)
        });
}



function _delete(req, res, next) {
    userService.delete(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}


async function getTrainings(req, res, next) {
    var promises = [];
    userService.getById(req.params.id)
        .then(function(user) {
            user.trainings.map(function(trainingId) {
                promises.push(trainingService.getById(trainingId));
            });
            return Promise.all(promises).then(data => res.json(data));

        })
        .catch(err => {
            next(err)
        });

}

function getSetups(req, res, next) {
    userService.getById(req.params.id)
        .then(user => {
            user ? res.json(user.setups) : res.sendStatus(404);
        })
        .catch(err => {
            next(err)
        });
}

function getTeams(req, res, next) {
    userService.getById(req.params.id)
        .then(user => {
            user ? res.json(user.teams) : res.sendStatus(404);
        })
        .catch(err => {
            next(err)
        });
}


async function getMaxPoints(req, res, next) {
    var points = [];
    userService.getById(req.params.id)
        .then(user => {
            user.trainings.forEach(item => {
                trainingService.getById(item).then(training => {
                    training.shots.map(shotId => {
                        points.push(shotService.getShotScore(shotId).then());
                    });
                    return Promise.all(points).then(data => {
                        const maxPoint = Math.max.apply(Math, data);
                        res.json(maxPoint);
                    })

                })

            })
        })
        .catch(err => {
            next(err)
        });
}

async function getAveragePoints(req, res, next) {
    var points = [];
    var sum = 0;
    userService.getById(req.params.id)
        .then(user => {
            user.trainings.forEach(item => {
                trainingService.getById(item).then(training => {
                    training.shots.map(shotId => {
                        points.push(shotService.getShotScore(shotId).then());
                    });

                    return Promise.all(points).then(data => {
                        data.forEach(item => {
                            sum += item;
                        })
                        var length = data.length;
                        var average = (sum / length).toFixed(1);
                        res.json({ "sum": sum, "length": length });
                        return


                    }).catch(err => {
                        return
                    })
                }).catch(err => {
                    return
                });

            })
        })
        .catch(err => {
            return
        });

}

async function test(req, res, next) {

}
async function getBestTrainings(req, res, next) {
    var array;
    var trainingAverage = [];
    var trainingPoints = [];
    var array = [];
    // var trainingPoints = [];
    var sumOfOneTraining = 0;
    userService.getById(req.params.id)
        .then(user => {
            user.trainings.forEach(item => {
                trainingService.getById(item).then(training => {
                    training.shots.forEach(shotId => {
                        shotService.getShotScore(shotId).then(point => {
                            trainingPoints.push(point);
                            array = Promise.all(trainingPoints);
                            // res.json(trainingPoints);
                        });
                    })

                })
                console.log(array);

                // trainingPoints.forEach(point => {
                //     console.log('itt');

                //     console.log('points', point)
                //     sumOfOneTraining += point;
                //     console.log('sum', sumOfOneTraining)

                //     var length = data.length;
                //     var average = (sumOfOneTraining / length).toFixed(2);
                //     trainingAverage.push({ item, average });
                //     console.log('before', trainingAverage);
                // })
            })

        })
        .catch(err => {
            next(err)
        });
}