import { combineReducers } from "redux";
import errorReducer from "../reducers/errorReducer";
import projectReducer from "./projectReducer";

export default combineReducers({
  errors: errorReducer,
  project: projectReducer,
});
