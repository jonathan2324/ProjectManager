import { combineReducers } from "redux";
import errorReducer from "../reducers/errorReducer";

export default combineReducers({
  errors: errorReducer,
});
