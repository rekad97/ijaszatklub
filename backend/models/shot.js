const mongoose = require('mongoose');
const Schema = mongoose.Schema;

// var trainingSchema = new mongooseSchema({
//     TrainingType: {
//         type: String,
//         enum: ['FITA'],
//         default: 'FITA'
//     },
// })

const shotModel = new Schema({
    trainingId: { type: mongoose.Schema.Types.ObjectId, ref: 'Training', required: true },
    target: { type: String },
    distance: { type: Number },
    placeX: { type: Number },
    placeY: { type: Number },
    score: { type: Number }

});

shotModel.set('toJSON', {
    virtuals: true,
    versionkey: false,
    transform: function(doc, ret) {
        delete ret._id;
    }
});

module.exports = mongoose.model('Shot', shotModel);