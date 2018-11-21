import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGamemaster, defaultValue } from 'app/shared/model/gamemaster.model';

export const ACTION_TYPES = {
  FETCH_GAMEMASTER_LIST: 'gamemaster/FETCH_GAMEMASTER_LIST',
  FETCH_GAMEMASTER: 'gamemaster/FETCH_GAMEMASTER',
  CREATE_GAMEMASTER: 'gamemaster/CREATE_GAMEMASTER',
  UPDATE_GAMEMASTER: 'gamemaster/UPDATE_GAMEMASTER',
  DELETE_GAMEMASTER: 'gamemaster/DELETE_GAMEMASTER',
  RESET: 'gamemaster/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGamemaster>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type GamemasterState = Readonly<typeof initialState>;

// Reducer

export default (state: GamemasterState = initialState, action): GamemasterState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMEMASTER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMEMASTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMEMASTER):
    case REQUEST(ACTION_TYPES.UPDATE_GAMEMASTER):
    case REQUEST(ACTION_TYPES.DELETE_GAMEMASTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMEMASTER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMEMASTER):
    case FAILURE(ACTION_TYPES.CREATE_GAMEMASTER):
    case FAILURE(ACTION_TYPES.UPDATE_GAMEMASTER):
    case FAILURE(ACTION_TYPES.DELETE_GAMEMASTER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEMASTER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEMASTER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMEMASTER):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMEMASTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMEMASTER):
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

const apiUrl = 'api/gamemasters';

// Actions

export const getEntities: ICrudGetAllAction<IGamemaster> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GAMEMASTER_LIST,
  payload: axios.get<IGamemaster>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IGamemaster> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEMASTER,
    payload: axios.get<IGamemaster>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IGamemaster> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMEMASTER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGamemaster> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMEMASTER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGamemaster> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMEMASTER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
