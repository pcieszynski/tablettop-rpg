import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMonster, defaultValue } from 'app/shared/model/monster.model';

export const ACTION_TYPES = {
  FETCH_MONSTER_LIST: 'monster/FETCH_MONSTER_LIST',
  FETCH_MONSTER: 'monster/FETCH_MONSTER',
  CREATE_MONSTER: 'monster/CREATE_MONSTER',
  UPDATE_MONSTER: 'monster/UPDATE_MONSTER',
  DELETE_MONSTER: 'monster/DELETE_MONSTER',
  RESET: 'monster/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMonster>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MonsterState = Readonly<typeof initialState>;

// Reducer

export default (state: MonsterState = initialState, action): MonsterState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MONSTER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MONSTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MONSTER):
    case REQUEST(ACTION_TYPES.UPDATE_MONSTER):
    case REQUEST(ACTION_TYPES.DELETE_MONSTER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MONSTER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MONSTER):
    case FAILURE(ACTION_TYPES.CREATE_MONSTER):
    case FAILURE(ACTION_TYPES.UPDATE_MONSTER):
    case FAILURE(ACTION_TYPES.DELETE_MONSTER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MONSTER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MONSTER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MONSTER):
    case SUCCESS(ACTION_TYPES.UPDATE_MONSTER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MONSTER):
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

const apiUrl = 'api/monsters';

// Actions

export const getEntities: ICrudGetAllAction<IMonster> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MONSTER_LIST,
  payload: axios.get<IMonster>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMonster> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MONSTER,
    payload: axios.get<IMonster>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMonster> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MONSTER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMonster> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MONSTER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMonster> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MONSTER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
