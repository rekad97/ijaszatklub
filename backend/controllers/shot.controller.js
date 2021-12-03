const express = require('express');
const router = express.Router();
const shotService = require('../services/shot.service');


router.post('/create', create);
router.get('/:id', getById);
router.get('/:id/score', getShotScore);
router.delete('/:id', _delete);

module.exports = router;


function getShotScore(req, res, next) {
    shotService.getShotScore(req.params.id)
        .then((shot) => res.json(shot))
        .catch(err => next(err));
}

function create(req, res, next) {
    shotService.create(req.body)
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