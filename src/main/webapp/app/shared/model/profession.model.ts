import { ICharacter } from 'app/shared/model//character.model';
import { ISkill } from 'app/shared/model//skill.model';

export interface IProfession {
  id?: number;
  name?: string;
  strengthModifier?: number;
  agilityModifier?: number;
  constituitionModifier?: number;
  intelligenceModifier?: number;
  willpowerModifier?: number;
  charismaModifier?: number;
  characters?: ICharacter[];
  skills?: ISkill[];
}

export const defaultValue: Readonly<IProfession> = {};
