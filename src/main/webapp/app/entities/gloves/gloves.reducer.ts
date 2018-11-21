import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGloves, defaultValue } from 'app/shared/model/gloves.model';

export const ACTION_TYPES = {
  FETCH_GLOVES_LIST: 'gloves/FETCH_GLOVES_LIST',
  FETCH_GLOVES: 'gloves/FETCH_GLOVES',
  CREATE_GLOVES: 'gloves/CREATE_GLOVES',
  UPDATE_GLOVES: 'gloves/UPDATE_GLOVES',
  DELETE_GLOVES: 'gloves/DELETE_GLOVES',
  RESET: 'gloves/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGloves>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type GlovesState = Readonly<typeof initialState>;

// Reducer

export default (state: GlovesState = initialState, action): GlovesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GLOVES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GLOVES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_GLOVES):
    case REQUEST(ACTION_TYPES.UPDATE_GLOVES):
    case REQUEST(ACTION_TYPES.DELETE_GLOVES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_GLOVES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GLOVES):
    case FAILURE(ACTION_TYPES.CREATE_GLOVES):
    case FAILURE(ACTION_TYPES.UPDATE_GLOVES):
    case FAILURE(ACTION_TYPES.DELETE_GLOVES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_GLOVES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_GLOVES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_GLOVES):
    case SUCCESS(ACTION_TYPES.UPDATE_GLOVES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_GLOVES):
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

const apiUrl = 'api/gloves';

// Actions

export const getEntities: ICrudGetAllAction<IGloves> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GLOVES_LIST,
  payload: axios.get<IGloves>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IGloves> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GLOVES,
    payload: axios.get<IGloves>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IGloves> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GLOVES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGloves> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GLOVES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGloves> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GLOVES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
