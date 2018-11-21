import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRightHand, defaultValue } from 'app/shared/model/right-hand.model';

export const ACTION_TYPES = {
  FETCH_RIGHTHAND_LIST: 'rightHand/FETCH_RIGHTHAND_LIST',
  FETCH_RIGHTHAND: 'rightHand/FETCH_RIGHTHAND',
  CREATE_RIGHTHAND: 'rightHand/CREATE_RIGHTHAND',
  UPDATE_RIGHTHAND: 'rightHand/UPDATE_RIGHTHAND',
  DELETE_RIGHTHAND: 'rightHand/DELETE_RIGHTHAND',
  RESET: 'rightHand/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRightHand>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RightHandState = Readonly<typeof initialState>;

// Reducer

export default (state: RightHandState = initialState, action): RightHandState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RIGHTHAND_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RIGHTHAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RIGHTHAND):
    case REQUEST(ACTION_TYPES.UPDATE_RIGHTHAND):
    case REQUEST(ACTION_TYPES.DELETE_RIGHTHAND):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RIGHTHAND_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RIGHTHAND):
    case FAILURE(ACTION_TYPES.CREATE_RIGHTHAND):
    case FAILURE(ACTION_TYPES.UPDATE_RIGHTHAND):
    case FAILURE(ACTION_TYPES.DELETE_RIGHTHAND):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RIGHTHAND_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RIGHTHAND):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RIGHTHAND):
    case SUCCESS(ACTION_TYPES.UPDATE_RIGHTHAND):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RIGHTHAND):
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

const apiUrl = 'api/right-hands';

// Actions

export const getEntities: ICrudGetAllAction<IRightHand> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RIGHTHAND_LIST,
  payload: axios.get<IRightHand>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRightHand> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RIGHTHAND,
    payload: axios.get<IRightHand>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRightHand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RIGHTHAND,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRightHand> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RIGHTHAND,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRightHand> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RIGHTHAND,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
