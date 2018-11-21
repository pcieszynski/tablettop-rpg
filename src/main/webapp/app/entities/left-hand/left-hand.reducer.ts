import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILeftHand, defaultValue } from 'app/shared/model/left-hand.model';

export const ACTION_TYPES = {
  FETCH_LEFTHAND_LIST: 'leftHand/FETCH_LEFTHAND_LIST',
  FETCH_LEFTHAND: 'leftHand/FETCH_LEFTHAND',
  CREATE_LEFTHAND: 'leftHand/CREATE_LEFTHAND',
  UPDATE_LEFTHAND: 'leftHand/UPDATE_LEFTHAND',
  DELETE_LEFTHAND: 'leftHand/DELETE_LEFTHAND',
  RESET: 'leftHand/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILeftHand>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type LeftHandState = Readonly<typeof initialState>;

// Reducer

export default (state: LeftHandState = initialState, action): LeftHandState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LEFTHAND_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LEFTHAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LEFTHAND):
    case REQUEST(ACTION_TYPES.UPDATE_LEFTHAND):
    case REQUEST(ACTION_TYPES.DELETE_LEFTHAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LEFTHAND_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LEFTHAND):
    case FAILURE(ACTION_TYPES.CREATE_LEFTHAND):
    case FAILURE(ACTION_TYPES.UPDATE_LEFTHAND):
    case FAILURE(ACTION_TYPES.DELETE_LEFTHAND):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LEFTHAND_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_LEFTHAND):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LEFTHAND):
    case SUCCESS(ACTION_TYPES.UPDATE_LEFTHAND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LEFTHAND):
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

const apiUrl = 'api/left-hands';

// Actions

export const getEntities: ICrudGetAllAction<ILeftHand> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_LEFTHAND_LIST,
  payload: axios.get<ILeftHand>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ILeftHand> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LEFTHAND,
    payload: axios.get<ILeftHand>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILeftHand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LEFTHAND,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ILeftHand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LEFTHAND,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILeftHand> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LEFTHAND,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
