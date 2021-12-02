const express = require('express');
const router = express.Router();
const userService = require('../services/user.service');
const trainingService = require('../services/training.service');
const { Training } = require('../database/db');

router.post('/auth', auth);
router.post('/register', register);
router.get('/', getAll);
router.get('/current', getCurrent);
router.get('/:id', getById);
router.get('/:id/trainings', getTrainings);
router.get('/:id/setups', getSetups);
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
    userService.getById(req.params.id)
        .then(user => {
            user ? res.json(user) : res.sendStatus(404);
            console.log("Usern in ID", user);
        })
        .catch(err => {
            console.log('getByid error', err);
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