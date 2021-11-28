const express = require('express');
const router = express.Router();
const shotService = require('../services/shot.service');


router.post('/trainings/:id/create', create);
router.get('/:id', getById);
router.delete('/:id', _delete);

module.exports = router;


function create(req, res, next) {
    shotService.create(req.params.id, req.body)
        .then((shot) => res.json(shot))
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