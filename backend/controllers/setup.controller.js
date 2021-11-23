const express = require('express');
const router = express.Router();
const setupService = require('../services/setup.service');


router.post('/create', create);
router.get('/', getAll);
router.get('/current', getCurrent);
router.get('/:id', getById);
router.delete('/:id', _delete);

module.exports = router;


function create(req, res, next) {
    setupService.create(req.body)
        .then(() => res.json({}))
        .catch(err => next(err));
}

function getAll(req, res, next) {
    setupService.getAll()
        .then(setups => res.json(setups))
        .catch(err => next(err));
}

function getCurrent(req, res, next) {
    setupService.getById(req.user.sub)
        .then(setup => setup ? res.json(setup) : res.sendStatus(404))
        .catch(err => next(err));
}


function getById(req, res, next) {
    setupService.getById(req.params.id)
        .then(setup => {
            setup ? res.json(setup) : res.sendStatus(404);
        })
        .catch(err => {
            next(err)
        });
}



function _delete(req, res, next) {
    setupService.delete(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}