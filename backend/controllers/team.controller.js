const express = require('express');
const router = express.Router();
const teamService = require('../services/team.service');


router.post('/users/:id/create', create);
router.get('/', getAll);
router.get('/:id/users', getTeamUsers);
router.get('/:id', getById);
router.get('/:id/users/trainings', getTrainingsFromTeamUsers);
router.delete('/:id', _delete);

module.exports = router;


function create(req, res, next) {
    teamService.create(req.params.id, req.body)
        .then((team) => res.json(team))
        .catch(err => next(err));
}

function getAll(req, res, next) {
    teamService.getAll()
        .then(teams => res.json(teams))
        .catch(err => next(err));
}

function getTeamUsers(req, res, next) {
    teamService.getById(req.params.id)
        .then(team => team ? res.json(team.users) : res.sendStatus(404))
        .catch(err => next(err));
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

function getTrainingsFromTeamUsers(req, res, next) {
    teamService.getTrainingsFromTeamUsers(req.params.id)
        .then(trainings => {
            trainings ? res.json(trainings) : res.sendStatus(404);
        })
        .catch(err => {
            next(err)
        });
}