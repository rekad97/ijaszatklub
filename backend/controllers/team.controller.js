const express = require('express');
const router = express.Router();
const teamService = require('../services/team.service');
const userService = require('../services/user.service');
const trainingService = require('../services/training.service');

router.post('/create', create);
router.post('/:teamId/:userId/add', addUserToTeam);
router.get('/', getAll);
router.get('/:id/users', getTeamUsers);
router.get('/:id', getById);
router.get('/:teamId/users/trainings', getTrainingsFromTeamUsers);
router.delete('/:id', _delete);
router.delete('/:teamId/delete/:userId', deleteUserFromTeam)

module.exports = router;


function create(req, res, next) {
    teamService.create(req.body)
        .then((team) => res.json(team))
        .catch(err => next(err));
}

function getAll(req, res, next) {
    teamService.getAll()
        .then(teams => res.json(teams))
        .catch(err => next(err));
}

function getTeamUsers(req, res, next) {
    var promises = [];
    teamService.getById(req.params.id)
        .then(function(team) {
            team.users.map(function(userId) {
                promises.push(userService.getById(userId));
            });
            return Promise.all(promises).then(data => res.json(data));

        })
        .catch(err => {
            next(err)
        });
}


function getById(req, res, next) {
    teamService.getById(req.params.id)
        .then(team => {
            team ? res.json(team) : res.sendStatus(404);
        })
        .catch(err => {
            next(err)
        });
}



function _delete(req, res, next) {
    teamService.delete(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}

async function getTrainingsFromTeamUsers(req, res, next) {
    var promises = [];
    await teamService.getById(req.params.teamId).then(function(team) {
        team.users.map(function(userId) {
            userService.getById(userId).then(function(user) {
                user.trainings.map(function(trainingId) {
                    promises.push(trainingService.getById(trainingId).then().catch());
                });
                return Promise.all(promises).then(data => res.json(data));

            })

        })
    }).catch(err => {
        next(err);
    })

}

function addUserToTeam(req, res, next) {
    teamService.addUser(req.params.teamId, req.params.userId)
        .then(() => res.json({}))
        .catch(err => {
            next(err)
        });
}

function deleteUserFromTeam(req, res, next) {
    teamService.deleteUser(req.params.teamId, req.params.userId)
        .then(() => res.json({}))
        .catch(err => {
            next(err)
        });
}