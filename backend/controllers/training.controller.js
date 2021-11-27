const express = require('express');
const router = express.Router();
const trainingService = require('../services/training.service');


router.post('/create', create);
router.get('/', getAll);
router.get('/current', getCurrent);
router.get('/:id', getById);
router.delete('/:id', _delete);

module.exports = router;


function create(req, res, next) {
    trainingService.create(req.body)
        .then((training) => res.json(training))
        .catch(err => next(err));
}

function getAll(req, res, next) {
    trainingService.getAll()
        .then(trainings => res.json(trainings))
        .catch(err => next(err));
}

function getCurrent(req, res, next) {
    trainingService.getById(req.user.sub)
        .then(training => training ? res.json(training) : res.sendStatus(404))
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



function _delete(req, res, next) {
    trainingService.delete(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}