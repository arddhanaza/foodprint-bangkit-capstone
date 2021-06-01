var model;
async function run() {
    const MODEL_PATH = 'model.json';
    model = await tf.loadLayersModel(MODEL_PATH);
    console.log(model.summary());
}

function classify() {
    var imageElement = document.getElementById('img_show');
    var raw  = tf.browser.fromPixels(imageElement).resizeBilinear([150,150]);
    var tensor = raw.expandDims();
    var prediction = model.predict(tensor);
    var result = prediction.as1D().argMax().dataSync()[0];
    var label = get_makanan(result);
    var bahan = get_bahan_dasar(result);
    var score = get_score(bahan);
    set_title(label,bahan,score);
    alert('Classified');
}

function get_makanan(result) {
    var labels = ['Ayam Goreng/ Bakar','Ayam Krispi','Bakso','Mi Ayam','Rendang Daging','Sate','Soto Ayam','Soto Daging'];
    return labels[result];
}
function get_bahan_dasar(result) {
    var component = ['Ayam','Ayam','Sapi','Ayam','Sapi','Ayam','Ayam','Sapi'];
    return component[result];
}

function get_score(result) {
    var score =  {
        Ayam : 4.56,
        Sapi : 32.5
    };
    return score[result];
}

function set_title(mkn, bhn, scr){

    var makanan = document.getElementById('span_makanan');
    var bahan = document.getElementById('span_bahan_dasar');
    var score = document.getElementById('span_prakiraaan');

    // makanan.appendChild(document.createTextNode(mkn));
    // bahan.appendChild(document.createTextNode(bhn));
    // score.appendChild(document.createTextNode(scr));
    makanan.innerText = mkn;
    bahan.innerText = bhn;
    score.innerText = scr;
}

document.addEventListener('DOMContentLoaded', run);