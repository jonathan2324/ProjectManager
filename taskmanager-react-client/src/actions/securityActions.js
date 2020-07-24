import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async (dispatch) => {
  try {
    await axios.post("/api/users/register", newUser);
    history.push("/login");
    dispatch({
      type: GET_ERRORS,
      payload: {},
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const login = (LoginRequest) => async (dispatch) => {
  try {
    //post => LoginRequest

    const res = await axios.post("/api/users/login", LoginRequest);
    //extract token from res.data
    //console.log(res.data);
    const { token } = res.data;
    //store the token in the local storage
    localStorage.setItem("jwtToken", token);
    //set our token in the headers
    setJWTToken(token);
    //decode token on REACT end
    const decoded = jwt_decode(token);
    //console.log(decoded);//produces the payload in the token
    //dispatch to securityReducer
    dispatch({
      type: SET_CURRENT_USER,
      payload: decoded,
    });
  } catch (err) {
    dispatch({
      type: GET_ERRORS,
      payload: err.response.data,
    });
  }
};

export const logout = () => (dispatch) => {
  localStorage.removeItem("jwtToken");

  //deletes the header under "Authorization"
  setJWTToken(false);

  //the valid token will turn to false
  dispatch({
    type: SET_CURRENT_USER,
    payload: {},
  });
};
