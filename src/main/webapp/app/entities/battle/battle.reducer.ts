import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBattle, defaultValue } from 'app/shared/model/battle.model';

export const ACTION_TYPES = {
  FETCH_BATTLE_LIST: 'battle/FETCH_BATTLE_LIST',
  FETCH_BATTLE: 'battle/FETCH_BATTLE',
  CREATE_BATTLE: 'battle/CREATE_BATTLE',
  UPDATE_BATTLE: 'battle/UPDATE_BATTLE',
  DELETE_BATTLE: 'battle/DELETE_BATTLE',
  RESET: 'battle/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBattle>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BattleState = Readonly<typeof initialState>;

// Reducer

export default (state: BattleState = initialState, action): BattleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BATTLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BATTLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BATTLE):
    case REQUEST(ACTION_TYPES.UPDATE_BATTLE):
    case REQUEST(ACTION_TYPES.DELETE_BATTLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BATTLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BATTLE):
    case FAILURE(ACTION_TYPES.CREATE_BATTLE):
    case FAILURE(ACTION_TYPES.UPDATE_BATTLE):
    case FAILURE(ACTION_TYPES.DELETE_BATTLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BATTLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BATTLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BATTLE):
    case SUCCESS(ACTION_TYPES.UPDATE_BATTLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BATTLE):
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

const apiUrl = 'api/battles';

// Actions

export const getEntities: ICrudGetAllAction<IBattle> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BATTLE_LIST,
  payload: axios.get<IBattle>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBattle> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BATTLE,
    payload: axios.get<IBattle>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBattle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BATTLE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBattle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BATTLE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBattle> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BATTLE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
