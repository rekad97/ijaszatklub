const express = require('express');
const router = express.Router();
const trainingService = require('../services/training.service');


router.post('/users/:id/create', create);
router.get('/:id', getById);
router.delete('/:id', _delete);
router.get('/:id/shots', getShots)
router.delete('/:id/shots', deleteShots)

module.exports = router;


function create(req, res, next) {
    trainingService.create(req.params.id, req.body)
        .then((training) => res.json(training))
        .catch(err => next(err));
}




function getById(req, res, next) {
    trainingService.getById(req.params.id)
        .then(training => {
            training ? res.json(training) : res.sendStatus(404);
        })
        .catch(err => {
            next(err)
        });
}

function getShots(req, res, next) {
    trainingService.getById(req.params.id)
        .then(training => {
            training ? res.json(training.shots) : res.sendStatus(404);
        })
        .catch(err => {
            next(err)
        });
}


function _delete(req, res, next) {
    trainingService.delete(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}


function deleteShots(req, res, next) {
    trainingService.deleteShots(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}