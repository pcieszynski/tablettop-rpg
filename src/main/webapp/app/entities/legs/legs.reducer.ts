import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILegs, defaultValue } from 'app/shared/model/legs.model';

export const ACTION_TYPES = {
  FETCH_LEGS_LIST: 'legs/FETCH_LEGS_LIST',
  FETCH_LEGS: 'legs/FETCH_LEGS',
  CREATE_LEGS: 'legs/CREATE_LEGS',
  UPDATE_LEGS: 'legs/UPDATE_LEGS',
  DELETE_LEGS: 'legs/DELETE_LEGS',
  RESET: 'legs/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILegs>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type LegsState = Readonly<typeof initialState>;

// Reducer

export default (state: LegsState = initialState, action): LegsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LEGS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LEGS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LEGS):
    case REQUEST(ACTION_TYPES.UPDATE_LEGS):
    case REQUEST(ACTION_TYPES.DELETE_LEGS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LEGS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LEGS):
    case FAILURE(ACTION_TYPES.CREATE_LEGS):
    case FAILURE(ACTION_TYPES.UPDATE_LEGS):
    case FAILURE(ACTION_TYPES.DELETE_LEGS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LEGS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LEGS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LEGS):
    case SUCCESS(ACTION_TYPES.UPDATE_LEGS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LEGS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/legs';

// Actions

export const getEntities: ICrudGetAllAction<ILegs> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LEGS_LIST,
  payload: axios.get<ILegs>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ILegs> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LEGS,
    payload: axios.get<ILegs>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILegs> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LEGS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILegs> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LEGS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILegs> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LEGS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
