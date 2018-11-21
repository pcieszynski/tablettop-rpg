import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHelmet, defaultValue } from 'app/shared/model/helmet.model';

export const ACTION_TYPES = {
  FETCH_HELMET_LIST: 'helmet/FETCH_HELMET_LIST',
  FETCH_HELMET: 'helmet/FETCH_HELMET',
  CREATE_HELMET: 'helmet/CREATE_HELMET',
  UPDATE_HELMET: 'helmet/UPDATE_HELMET',
  DELETE_HELMET: 'helmet/DELETE_HELMET',
  RESET: 'helmet/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHelmet>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type HelmetState = Readonly<typeof initialState>;

// Reducer

export default (state: HelmetState = initialState, action): HelmetState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HELMET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HELMET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_HELMET):
    case REQUEST(ACTION_TYPES.UPDATE_HELMET):
    case REQUEST(ACTION_TYPES.DELETE_HELMET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_HELMET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HELMET):
    case FAILURE(ACTION_TYPES.CREATE_HELMET):
    case FAILURE(ACTION_TYPES.UPDATE_HELMET):
    case FAILURE(ACTION_TYPES.DELETE_HELMET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_HELMET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_HELMET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_HELMET):
    case SUCCESS(ACTION_TYPES.UPDATE_HELMET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_HELMET):
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

const apiUrl = 'api/helmets';

// Actions

export const getEntities: ICrudGetAllAction<IHelmet> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_HELMET_LIST,
  payload: axios.get<IHelmet>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IHelmet> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HELMET,
    payload: axios.get<IHelmet>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IHelmet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HELMET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHelmet> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HELMET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHelmet> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HELMET,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
