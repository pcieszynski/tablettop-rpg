import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISkill, defaultValue } from 'app/shared/model/skill.model';

export const ACTION_TYPES = {
  FETCH_SKILL_LIST: 'skill/FETCH_SKILL_LIST',
  FETCH_SKILL: 'skill/FETCH_SKILL',
  CREATE_SKILL: 'skill/CREATE_SKILL',
  UPDATE_SKILL: 'skill/UPDATE_SKILL',
  DELETE_SKILL: 'skill/DELETE_SKILL',
  SET_BLOB: 'skill/SET_BLOB',
  RESET: 'skill/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISkill>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SkillState = Readonly<typeof initialState>;

// Reducer

export default (state: SkillState = initialState, action): SkillState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SKILL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SKILL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SKILL):
    case REQUEST(ACTION_TYPES.UPDATE_SKILL):
    case REQUEST(ACTION_TYPES.DELETE_SKILL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SKILL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SKILL):
    case FAILURE(ACTION_TYPES.CREATE_SKILL):
    case FAILURE(ACTION_TYPES.UPDATE_SKILL):
    case FAILURE(ACTION_TYPES.DELETE_SKILL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SKILL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SKILL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SKILL):
    case SUCCESS(ACTION_TYPES.UPDATE_SKILL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SKILL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/skills';

// Actions

export const getEntities: ICrudGetAllAction<ISkill> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SKILL_LIST,
  payload: axios.get<ISkill>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISkill> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SKILL,
    payload: axios.get<ISkill>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISkill> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SKILL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISkill> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SKILL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISkill> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SKILL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
