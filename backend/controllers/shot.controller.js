const express = require('express');
const router = express.Router();
const shotService = require('../services/shot.service');


router.post('/create', create);
router.get('/', getAll);
router.get('/current', getCurrent);
router.get('/:id', getById);
router.delete('/:id', _delete);

module.exports = router;


function create(req, res, next) {
    shotService.create(req.body)
        .then(() => res.json({}))
        .catch(err => next(err));
}

function getAll(req, res, next) {
    shotService.getAll()
        .then(shots => res.json(shots))
        .catch(err => next(err));
}

function getCurrent(req, res, next) {
    shotService.getById(req.user.sub)
        .then(shot => shot ? res.json(shot) : res.sendStatus(404))
        .catch(err => next(err));
}


function getById(req, res, next) {
    shotService.getById(req.params.id)
        .then(shot => {
            shot ? res.json(shot) : res.sendStatus(404);
        })
        .catch(err => {
            next(err)
        });
}



function _delete(req, res, next) {
    shotService.delete(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}