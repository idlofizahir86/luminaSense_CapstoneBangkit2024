const { Firestore } = require('@google-cloud/firestore');

async function createUser(data) {
    const db = new Firestore({
        databaseId: "lumina-sense-database"
    });

    const userCollection = db.collection('users');
    await userCollection.doc(data.id).set(data);
}

async function getUserbyemail (email) {
    const db = new Firestore({
        databaseId: "lumina-sense-database"
    });

    const userByemail = await db.collection('users').where("email", "==", email).get();

    let data = [];
    userByemail.forEach(doc => {
        data.push(doc.data());
    });

    return data;
}

module.exports = {createUser, getUserbyemail};