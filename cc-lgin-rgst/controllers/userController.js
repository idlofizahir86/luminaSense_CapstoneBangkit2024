// const User = require('../models/user');
const bcrypt = require('bcryptjs');
// const Joi = require('@hapi/joi');
const crypto = require('crypto');
const {createUser, getUserbyemail} = require('../services/userServices');
const { generateToken } = require('../services/authServices');

const register = async (request, h) => {
    const { name, email, password, passwordConfirmation } = request.payload;
    const id = crypto.randomUUID();
    const hashedPassword = await bcrypt.hash(password, 10);
    const data = {
        "id": id,
        "name": name,
        "email": email,
        "password": hashedPassword
    }

    const isValid = await bcrypt.compare(passwordConfirmation, hashedPassword); //password
    if(!isValid){
       return h.response({message: "password tidak valid"}).code(400); 
    }
    
    const users = await getUserbyemail(data.email);
    const isNotTaken = !(users.length);

    if(!isNotTaken){
        return h.response({message: "email sudah digunakan"}).code(400); 
    }

    try {
        await createUser(data);
        
        return h.response({message : "registrasi berhasil"}).code(201);
    } catch (err) {
        return h.response(err).code(500);
    }
};

const login = async (request, h) => {
    // const schema = Joi.object({
    //     email: Joi.string().email().required(),
    //     password: Joi.string().required()
    // });

    const { email, password } = request.payload;

    try {
        const users = await getUserbyemail(email);
        const isNotTaken = !(users.length);

        if(isNotTaken){
            return h.response({message: "email tidak ditemukan"}).code(404); 
        }

        const user = users[0];

        if (!await bcrypt.compare(password, user.password)) {
            return h.response({ message: 'Invalid email or password' }).code(401);
        }

        const token = generateToken(user);

        return h.response({token}).code(200);
    } catch (err) {
        return h.response(err).code(500);
    }
};

module.exports = {
    register,
    login
};
