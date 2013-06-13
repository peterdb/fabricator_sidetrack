package fabricator.stories

import fabricator.Fabricator
import fabricator.dsl.DSL
import fabricator.support.Story
import fabricator.support.User

description "traits"

scenario "traits", {
	given "a factory is defined with aliases", {
		Fabricator.define {
			factory(User, aliases: ["author"])
			
			factory(Story) {
				title "My awesome story"
				author

				trait "published" {
					published true
				}

				trait "unpublished" {
					published false
				}
			
//			  trait :week_long_publishing do
//				start_at { 1.week.ago }
//				end_at   { Time.now }
//			  end
//			
//			  trait :month_long_publishing do
//				start_at { 1.month.ago }
//				end_at   { Time.now }
//			  end
			
//			  factory :week_long_published_story,    traits: [:published, :week_long_publishing]
//			  factory :month_long_published_story,   traits: [:published, :month_long_publishing]
//			  factory :week_long_unpublished_story,  traits: [:unpublished, :week_long_publishing]
//			  factory :month_long_unpublished_story, traits: [:unpublished, :month_long_publishing]
  
				factory "published", traits: [published]
			}
		}
	}
	
	when "blah", {
		story = Fabricator.fabricate(Story)
	}
	
	then "a lookup with the alias should return the factory", {
		println story
	}
}
