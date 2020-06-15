import { combineReducers } from "redux";
import errorReducer from "../reducers/errorReducer";
import projectReducer from "./projectReducer";
import backlogReducer from "./backlogReducer";

export default combineReducers({
  errors: errorReducer,
  project: projectReducer,
  backlog: backlogReducer,
});
