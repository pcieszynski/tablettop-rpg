import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMonsterType, defaultValue } from 'app/shared/model/monster-type.model';

export const ACTION_TYPES = {
  FETCH_MONSTERTYPE_LIST: 'monsterType/FETCH_MONSTERTYPE_LIST',
  FETCH_MONSTERTYPE: 'monsterType/FETCH_MONSTERTYPE',
  CREATE_MONSTERTYPE: 'monsterType/CREATE_MONSTERTYPE',
  UPDATE_MONSTERTYPE: 'monsterType/UPDATE_MONSTERTYPE',
  DELETE_MONSTERTYPE: 'monsterType/DELETE_MONSTERTYPE',
  RESET: 'monsterType/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMonsterType>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MonsterTypeState = Readonly<typeof initialState>;

// Reducer

export default (state: MonsterTypeState = initialState, action): MonsterTypeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MONSTERTYPE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MONSTERTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MONSTERTYPE):
    case REQUEST(ACTION_TYPES.UPDATE_MONSTERTYPE):
    case REQUEST(ACTION_TYPES.DELETE_MONSTERTYPE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MONSTERTYPE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MONSTERTYPE):
    case FAILURE(ACTION_TYPES.CREATE_MONSTERTYPE):
    case FAILURE(ACTION_TYPES.UPDATE_MONSTERTYPE):
    case FAILURE(ACTION_TYPES.DELETE_MONSTERTYPE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MONSTERTYPE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MONSTERTYPE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MONSTERTYPE):
    case SUCCESS(ACTION_TYPES.UPDATE_MONSTERTYPE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MONSTERTYPE):
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

const apiUrl = 'api/monster-types';

// Actions

export const getEntities: ICrudGetAllAction<IMonsterType> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MONSTERTYPE_LIST,
  payload: axios.get<IMonsterType>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMonsterType> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MONSTERTYPE,
    payload: axios.get<IMonsterType>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMonsterType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MONSTERTYPE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMonsterType> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MONSTERTYPE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMonsterType> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MONSTERTYPE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
