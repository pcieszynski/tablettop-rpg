import { IProfession } from 'app/shared/model//profession.model';
import { ICharacter } from 'app/shared/model//character.model';

export interface ISkill {
  id?: number;
  name?: string;
  description?: any;
  damage?: string;
  heal?: number;
  level?: number;
  professions?: IProfession[];
  characters?: ICharacter[];
}

export const defaultValue: Readonly<ISkill> = {};
