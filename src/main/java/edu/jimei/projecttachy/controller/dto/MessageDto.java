package edu.jimei.projecttachy.controller.dto;

import edu.jimei.projecttachy.entity.Message;

public record MessageDto(long id, String type, String content, String avatar, boolean isTable) {

    public static MessageDto fromEntity(Message message) {
        // TODO: The avatar URL and isTable flag should be determined by logic,
        // perhaps based on the agent or message metadata.
        String avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCtSYF8Yxc5_NrvJcw0ezjYV5rluO05Uo-y0cSf8LFtyeYvy8gjtzsVQZnse1lxKFtgwC_XAXUX93QBR_8J3ew6kwkaRubup5ZA2CM_9R879EM6ZzKCRZ77QfnHrl44ytByl-cjnJf8M4Yid4Gfue0PcnWLuVUT-FCi-uZHI0JizgRG7ySeosWL9RCUjZtPUJ0N412zAoCEdycYCX5FCJommYKM9dIz7PcXiu8NgTLOUk4zIcRAgZXq87QW59CVGjyHMXBmd4sTmKWK";
        boolean isTable = message.getMetadata() != null && message.getMetadata().getOrDefault("isTable", false).equals(true);

        return new MessageDto(
                message.getId(),
                message.getSenderType().name(),
                message.getContent(),
                avatarUrl,
                isTable
        );
    }
} 